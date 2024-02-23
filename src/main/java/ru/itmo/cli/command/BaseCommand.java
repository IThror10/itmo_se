package ru.itmo.cli.command;

import lombok.Data;
import ru.itmo.cli.descriptor.IDescriptor;

@Data
public abstract class BaseCommand {
    public abstract void execute();

    public final void changeInOut(IDescriptor descriptor, int number) {

    }

    private CommandData data;
}
