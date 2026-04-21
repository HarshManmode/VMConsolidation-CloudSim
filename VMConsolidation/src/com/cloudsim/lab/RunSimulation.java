package com.cloudsim.lab;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.*;
import org.cloudbus.cloudsim.power.models.PowerModelLinear;
import org.cloudbus.cloudsim.provisioners.*;
import java.util.*;

public class RunSimulation {

    static int NUM_HOSTS = 25;
    static int NUM_VMS   = 50;

    public static void main(String[] args) throws Exception {

        CloudSim.init(1, Calendar.getInstance(), false);

        List<PowerHost> hostList = new ArrayList<>();
        for (int i = 0; i < NUM_HOSTS; i++) {
            List<Pe> peList = new ArrayList<>();
            for (int j = 0; j < 4; j++)
                peList.add(new Pe(j, new PeProvisionerSimple(1000)));

            hostList.add(new PowerHostUtilizationHistory(
                i,
                new RamProvisionerSimple(16384),
                new BwProvisionerSimple(10000),
                1000000,
                peList,
                new VmSchedulerTimeSharedOverSubscription(peList),
                new PowerModelLinear(250, 93.7)
            ));
        }

        DatacenterCharacteristics chars = new DatacenterCharacteristics(
            "x86", "Linux", "Xen", hostList, 10.0, 3e-3, 0.05, 0.001, 0.0);

        PowerVmAllocationPolicyMigrationAbstract vmPolicy =
            new PowerVmAllocationPolicyMigrationStaticThreshold(
                hostList,
                new PowerVmSelectionPolicyMaximumCorrelation(
                    new PowerVmSelectionPolicyMinimumMigrationTime()
                ),
                0.8
            );

        PowerDatacenter datacenter = new PowerDatacenter(
            "Datacenter", chars, vmPolicy, new LinkedList<>(), 300
        );
        datacenter.setDisableMigrations(false);

        DatacenterBroker broker = new DatacenterBroker("Broker");

        List<Vm> vmList = new ArrayList<>();
        for (int i = 0; i < NUM_VMS; i++) {
            vmList.add(new Vm(
                i, broker.getId(),
                500, 1, 512, 1000, 10000,
                "Xen", new CloudletSchedulerTimeShared()
            ));
        }
        broker.submitVmList(vmList);

        // Long-running cloudlets so simulation has time to accumulate power
        List<Cloudlet> cloudletList = new ArrayList<>();
        Random rnd = new Random(42);
        for (int i = 0; i < NUM_VMS; i++) {
            double load = 0.4 + rnd.nextDouble() * 0.5; // 40-90% util
            UtilizationModel um = new UtilizationModelFull();
            Cloudlet cl = new Cloudlet(i, 5000000, 1, 300, 300, um, um, um);
            cl.setUserId(broker.getId());
            cl.setVmId(i);
            cloudletList.add(cl);
        }
        broker.submitCloudletList(cloudletList);

        CloudSim.startSimulation();
        CloudSim.stopSimulation();

        // Compute energy from each host
        double totalEnergy = 0;
        int overloaded = 0;
        for (PowerHost h : hostList) {
            double u = h.getUtilizationOfCpu();
            // PowerModelLinear: P = Pmax * (k + (1-k)*u), k = 93.7/250
            double pIdle = 93.7, pMax = 250.0;
            double power = pIdle + (pMax - pIdle) * u;
            totalEnergy += power * 300; // watts * seconds
            if (u > 0.8) overloaded++;
        }
        double energyKwh = totalEnergy / (3600.0 * 1000.0);
        double slaViol = (overloaded / (double) NUM_HOSTS) * 100.0;
        int migrations = datacenter.getMigrationCount();

        System.out.println("\n===== SIMULATION RESULTS =====");
        System.out.printf("Hosts : %d | VMs : %d%n", NUM_HOSTS, NUM_VMS);
        System.out.printf("Energy consumed  : %.4f kWh%n", energyKwh);
        System.out.printf("Migrations       : %d%n", migrations);
        System.out.printf("SLA Violation    : %.2f%%%n", slaViol);
        System.out.println("==============================");
    }
}