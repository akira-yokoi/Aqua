package org.amd.aqua.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akira on 2018/04/21.
 */

public class MachineManager {
    private static MachineManager instance = null;

    private List<Machine> machineList = new ArrayList<>();

    public static MachineManager getInstance() {
        if (instance == null) {
            instance = new MachineManager();
        }
        return instance;
    }

    public void addMachine(Machine machine) {
        machineList.add(machine);
    }

    public void setMachines(List<Machine> machines) {
        machineList.clear();
        machineList.addAll( machines);
    }

    public List<Machine> getMachines() {
        return machineList;
    }
}
