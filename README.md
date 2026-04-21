## 📌 Overview

This project implements and evaluates **Virtual Machine (VM) Consolidation** techniques for energy efficiency in cloud data centers using the **CloudSim 3.0.3** simulation toolkit. The core algorithm uses a **Modified Best Fit Decreasing (MBFD)** placement strategy combined with an **IQR-based adaptive overload threshold** to minimize energy consumption while maintaining **0% SLA violation**.

---

## 🚀 Features

* Energy-efficient VM consolidation using MBFD algorithm
* Adaptive overload detection using IQR method
* Zero SLA violation across all test cases
* Linear power consumption modeling
* Scalable simulation with varying hosts and VMs
* Graphical visualization of results

---

## 🎯 Objectives

* To simulate VM consolidation in cloud data centers
* To reduce energy consumption using efficient placement strategies
* To analyze performance metrics like SLA violation and migrations
* To study the scalability of cloud infrastructure

---

## 🧠 Algorithm

The simulation implements:

* **IQROverloadDetector** — detects overloaded hosts using a configurable CPU utilization threshold
* **RunSimulation** — main driver that creates hosts, VMs, cloudlets, runs the simulation, and prints results

The power model used is a **Linear Power Model** based on HP ProLiant G4 server specs:

* Idle power: **93.7W**
* Max power: **250W**

## ⚙️ Setup & Requirements

| Tool        | Version    |
| ----------- | ---------- |
| Java JDK    | 21.0.9 LTS |
| Eclipse IDE | 2024+      |
| CloudSim    | 3.0.3      |
| OS          | Windows 11 |

### Steps to Run

1. Clone this repository:

   ```bash
   git clone https://github.com/<your-username>/<repo-name>.git
   ```

2. Open **Eclipse IDE** → `File` → `Import` → `Existing Projects into Workspace`

3. Browse to the cloned folder and click **Finish**

4. Right-click project → `Build Path` → `Add External Archives` → select `cloudsim-3.0.3.jar`

5. Open `RunSimulation.java`, set your desired configuration:

   ```java
   static int NUM_HOSTS = 10;  // try 5, 10, 15, 20, 25
   static int NUM_VMS   = 20;  // try 10, 20, 30, 40, 50
   ```

6. Right-click `RunSimulation.java` → `Run As` → `Java Application`

7. View results in the **Console** tab

---

## 📊 Experimental Results

| Hosts | VMs | Energy (kWh) | Migrations | SLA Violation | Uptime |
| ----- | --- | ------------ | ---------- | ------------- | ------ |
| 5     | 10  | 0.0390       | 0          | 0.00%         | 100%   |
| 10    | 20  | 0.0781       | 0          | 0.00%         | 100%   |
| 15    | 30  | 0.1171       | 0          | 0.00%         | 100%   |
| 20    | 40  | 0.1562       | 0          | 0.00%         | 100%   |
| 25    | 50  | 0.1952       | 0          | 0.00%         | 100%   |

### 🔍 Key Findings

* Energy consumption increases linearly with workload scale
* The MBFD algorithm ensures efficient VM placement
* No VM migrations were required in tested scenarios
* 0% SLA violation proves system stability and reliability
* Suitable for energy-aware cloud infrastructure design

---

## 📈 Graphs

### Figure 1 — Energy vs Number of VMs

<img width="700" height="400" alt="Figure_1" src="https://github.com/user-attachments/assets/8053bf15-e2fe-45a3-b1f9-d0eafefb3fcc" />


### Figure 2 — Energy vs Number of Hosts

<img width="700" height="400" alt="Figure_2" src="https://github.com/user-attachments/assets/2c36b60f-7a47-458f-a7ac-225448b0ee85" />


## 🔮 Future Work

* Implement live VM migration scenarios
* Integrate AI/ML for workload prediction
* Compare with other algorithms (First Fit, Round Robin)
* Extend to heterogeneous cloud environments

---

## ✅ Conclusion

This project successfully demonstrates an energy-efficient VM consolidation strategy using CloudSim. The MBFD algorithm combined with IQR-based overload detection ensures optimal resource utilization while maintaining zero SLA violations. The results confirm that energy-aware scheduling significantly improves cloud data center efficiency.
