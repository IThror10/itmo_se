package ru.itmo.cli.command;

import lombok.Data;
import ru.itmo.cli.descriptor.IDescriptor;

@Data
public abstract class BaseCommand {
    public abstract void execute();

    /** 
     * @param descriptor
     * @param number
     */
    public final void changeInOut(IDescriptor descriptor, int number) {

    }

    public CommandData getCommandData() {
        return data;
    }
    private CommandData data;
}
