package ru.itmo.cli.command;

import lombok.Data;
import ru.itmo.cli.descriptor.IDescriptor;

@Data
public class CommandData {
    private CommandStatus status;
    private IDescriptor stdin;
    private IDescriptor stdout;
    private IDescriptor stderr;
}
