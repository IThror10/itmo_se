package ru.itmo.cli.descriptor;

public class DefaultOutDescriptor implements IDescriptor {
    
    /** 
     * @param out
     */
    @Override
    public void write(String out) {
        System.out.print(out);
    }

    @Override
    public String read() {
        return null;
    }
}
