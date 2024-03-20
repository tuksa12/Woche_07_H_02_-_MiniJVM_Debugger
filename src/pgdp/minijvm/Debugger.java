package pgdp.minijvm;

import pgdp.MiniJava;

import java.util.ArrayList;
import java.util.Locale;

public class Debugger {
    private int stackSize;
    private Instruction[] code;
    private Simulator simulation;
    private int[] breakpoint;

    //Attributes to be used later in the undo() method
    private String lastInstruction = null;
    private String lastLastInstruction = null;
    private String lastLastLastInstruction = null;



    public static Instruction parseInstruction(String string) {
        string = string.toUpperCase();

        if (string.equals("ADD")) {
            return new Add();
        } else if (string.equals("SUB")) {
            return new Sub();
        } else if (string.contains("ALLOC")) {
            int k = Character.getNumericValue(string.charAt(6));
            return new Alloc(k);
        } else if (string.contains("CONST")) {
            int k = Character.getNumericValue(string.charAt(6));
            return new Const(k);
        } else if (string.contains("FJUMP")) {
            int k = Character.getNumericValue(string.charAt(6));
            return new FJump(k);
        } else if (string.contains("JUMP")) {
            int k = Character.getNumericValue(string.charAt(5));
            return new Jump(k);
        } else if (string.equals("HALT")) {
            return new Halt();
        } else if (string.equals("LESS")) {
            return new Less();
        } else if (string.contains("LOAD")) {
            int k = Character.getNumericValue(string.charAt(5));
            return new Load(k);
        } else if (string.contains("STORE")) {
            int k = Character.getNumericValue(string.charAt(6));
            return new Store(k);
        }
        return null;
    }

    //Method to convert an Array os strings into an Array os Instructions(to help in
    // the Debugger constructor)
    private static Instruction[] conversion(String[] commands) {
        Instruction[] result = new Instruction[commands.length];
        for (int i = 0; i < commands.length; i++) {
            result[i] = parseInstruction(commands[i]);
        }
        return result;
    }

    //Debbuger constructors
    public Debugger(int stackSize, String[] instructionsAsStrings) {
        this(stackSize, conversion(instructionsAsStrings));
    }

    public Debugger(int stackSize, Instruction[] code) {
        this.stackSize = stackSize;
        this.code = code;
        this.simulation = new Simulator(stackSize, code);
        this.breakpoint = new int[code.length + 1];

    }

    //Methods to implement each debbuger instruction
    public String setBreakpoint(int index) {
        lastInstruction = "SET-BREAKPOINT " + index;
        if (index < 0 || index > code.length) {
            return "Invalid breakpoint index!" + "\n";
        } else if (breakpoint[index] == 1) {
            return "Breakpoint already set!" + "\n";
        } else {
            lastLastLastInstruction = lastLastInstruction; //This is for the undo method,comes later
            lastLastInstruction = lastInstruction;
            lastInstruction = "SET-BREAKPOINT " + index;
            breakpoint[index] = 1;
            return null;
        }

    }

    public String removeBreakpoint(int index) {
        if (index < 0 || index > code.length) {
            return "Invalid breakpoint index!" + "\n";
        } else if (breakpoint[index] != 1) {
            return "No breakpoint to remove!" + "\n";
        } else {
            lastLastLastInstruction = lastLastInstruction;
            lastLastInstruction = lastInstruction;
            lastInstruction = "REMOVE-BREAKPOINT " + index;
            breakpoint[index] = 0;
            return null;
        }
    }

    public String run() {
        if (simulation.isHalted()) {
            return "No more instructions to execute!" + "\n";
        }
        lastLastLastInstruction = lastLastInstruction;
        lastLastInstruction = lastInstruction;
        lastInstruction = "RUN";
        for (int i = simulation.getProgramCounter(); i < code.length; i++) {
            if (breakpoint != null && breakpoint[i] != 0) {
                break;
            } else
                simulation.executeNextInstruction();

        }
        return null;
    }

    public String next(int k) {
        if (k < 0) {
            return "Instruction count must be positive!" + "\n";
        }
        if (simulation.isHalted()) {
            return "No more instructions to execute!" + "\n";
        }
        lastLastLastInstruction = lastLastInstruction;
        lastLastInstruction = lastInstruction;
        lastInstruction = "NEXT " + k;
        for (int i = 0; i < k; i++) {
            if (breakpoint != null && breakpoint[i] != 0) {
                break;
            } else{
                simulation.executeNextInstruction();
            }
        }
        return null;
    }

    public String step() {
        int i = simulation.getProgramCounter();
        if (simulation.isHalted() && i == code.length) {
            return "No more instructions to execute!" + "\n";
        } else {
            lastLastLastInstruction = lastLastInstruction;
            lastLastInstruction = lastInstruction;
            lastInstruction = "STEP";
            simulation.executeNextInstruction();
            return null;
        }
    }

    public String reset() {
        lastLastLastInstruction = lastLastInstruction;
        lastLastInstruction = lastInstruction;
        lastInstruction = "RESET";
        Simulator result = new Simulator(stackSize, code);
        simulation = result;
        return null;
    }

    public String back(){
        if (simulation.getProgramCounter() == 0){
            return "Cannot go back an instruction, none left!" + "\n";
        }else{
            lastLastLastInstruction = lastLastInstruction;
            lastLastInstruction = lastInstruction;
            lastInstruction = "BACK";
            Simulator result = new Simulator(stackSize,code);
            for (int i = 0; i < simulation.getProgramCounter()-1 ; i++) {
                result.executeNextInstruction();
            }
            simulation = result;
        return null;
        }
    }

    //I wasn't able to make the InstructionStack work for this method
    //so i used variables to 'store' the last instructions and the ones
    //before to undo them.
    public String undo(){
        if (lastInstruction == null){
            return "No debugger command to undo!" + "\n";
        }else{
            if(lastInstruction.contains("SET-BREAKPOINT")){
                int k = Character.getNumericValue(lastInstruction.charAt(15));
                removeBreakpoint(k);
            }else if(lastInstruction.contains("REMOVE-BREAKPOINT")){
                int k = Character.getNumericValue(lastInstruction.charAt(18));
                setBreakpoint(k);
            }else if (lastInstruction.contains("RUN")){
                reset();
            }else if (lastInstruction.contains("NEXT")){
                int k = Character.getNumericValue(lastInstruction.charAt(5));
                for (int i = 0; i < k; i++) {
                    back();
                }
                lastLastInstruction = "NEXT " + k;

            }else if (lastInstruction.contains("STEP")){
                back();
            }else if (lastInstruction.contains("RESET")){
                run();
            }else if (lastInstruction.contains("BACK")){
                step();
            }else if (lastInstruction.contains("UNDO")){
                if(lastLastInstruction.contains("SET-BREAKPOINT")){
                    int k = Character.getNumericValue(lastLastInstruction.charAt(15));
                    setBreakpoint(k);
                }else if(lastLastInstruction.contains("REMOVE-BREAKPOINT")){
                    int k = Character.getNumericValue(lastLastInstruction.charAt(18));
                    removeBreakpoint(k);
                }else if(lastLastInstruction.contains("RUN")){
                    run();
                }else if(lastLastInstruction.contains("NEXT")){
                    int k = Character.getNumericValue(lastLastInstruction.charAt(5));
                    next(k);
                }else if(lastLastInstruction.contains("STEP")){
                    step();
                }else if(lastLastInstruction.contains("RESET")){
                    reset();
                }else if(lastLastInstruction.contains("BACK")){
                    back();
                }else if(lastLastInstruction.contains("UNDO")){
                    if(lastLastLastInstruction.contains("SET-BREAKPOINT")){
                        int k = Character.getNumericValue(lastLastInstruction.charAt(15));
                        removeBreakpoint(k);
                    }else if(lastLastLastInstruction.contains("REMOVE-BREAKPOINT")){
                        int k = Character.getNumericValue(lastLastInstruction.charAt(18));
                        setBreakpoint(k);
                    }else if(lastLastLastInstruction.contains("RUN")){
                        reset();
                    }else if(lastLastLastInstruction.contains("NEXT")){
                        int k = Character.getNumericValue(lastLastLastInstruction.charAt(5));
                        for (int i = 0; i < k; i++) {
                            back();
                        }
                    }else if(lastLastLastInstruction.contains("STEP")){
                        back();
                    }else if(lastLastLastInstruction.contains("RESET")){
                        run();
                    }else if(lastLastLastInstruction.contains("BACK")){
                        step();
                    }else{
                        return "No debugger command to undo!"+"\n";
                    }

            }else{
                return "No debugger command to undo!"+"\n";
            }
        }
        lastInstruction = "UNDO";
        return null;
        }
    }

    public Simulator getSimulator() {
        return this.simulation;
    }

    public String executeDebuggerCommand(String command) {
        command = command.toUpperCase();

        if (command.contains("SET-BREAKPOINT")) {
            int k = Character.getNumericValue(command.charAt(15));
            String result = setBreakpoint(k);
            if (result != null) {
                return result;
            }
        } else if (command.contains("REMOVE-BREAKPOINT")) {
            int k = Character.getNumericValue(command.charAt(18));
            String result = removeBreakpoint(k);
            if (result != null) {
                return result;
            }
        } else if (command.equals("RUN")) {
            String result = run();
            if (result != null) {
                return result;
            }
        } else if (command.contains("NEXT")) {
            int k = Character.getNumericValue(command.charAt(5));
            String result = next(k);
            if (result != null) {
                return result;
            }
        } else if (command.equals("STEP")) {
            String result = step();
            if (result != null) {
                return result;
            }
        } else if (command.equals("RESET")) {
            String result = reset();
            if (result != null) {
                return result;
            }
        }else if (command.equals("BACK")){
            String result = back();
            if (result != null) {
                return result;
            }
        } else if (command.equals("UNDO")) {
            String result = undo();
            if (result != null) {
                return result;
            }
        } else {
            return "Unknown debugger command!"+"\n";
        }
        return null;
    }

    public void debugInteractively(){
        boolean run = true;
        while(run){
            System.out.print(simulation.toString());
            String command  = MiniJava.readString("\n"+"Input debugger command:");
            if (!command.toUpperCase().equals("EXIT")){
                String result = executeDebuggerCommand(command);
                if(result != null){
                    System.out.print(result);
                }
            }else{
                run = false;
            }
        }

    }

    public static void main (String[] args){
        int size = 1;
        ArrayList<String> arrayList = new ArrayList();//ArrayList to store each input from the user
        String input = "1";
        while(!input.equals("")){
            input = MiniJava.readString("Please enter the next instruction or press Enter to complete the input:");
            if(!input.equals("")){
                arrayList.add(input);
            }
        }
        //Conversion of the arrayList to an StringArray
        String[] stringArray = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            stringArray[i] = arrayList.get(i);
        }
        int stackSize = MiniJava.readInt("Please enter the stack size for the MiniJVM:");
        Debugger debugg = new Debugger(stackSize,stringArray);//stackSize given by the user/stringArray that used to be an ArrayList
        debugg.debugInteractively();
    }

}