package com.cloudsim.lab;

import org.cloudbus.cloudsim.power.PowerHost;

public class IQROverloadDetector {

    private final double threshold;

    public IQROverloadDetector(double threshold) {
        this.threshold = threshold;
    }

    public boolean isOverloaded(PowerHost host) {
        return host.getUtilizationOfCpu() > threshold;
    }
}