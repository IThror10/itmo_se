package ru.itmo.cli.command;

import lombok.Data;
import ru.itmo.cli.descriptor.IDescriptor;

/**
 * Represents the data associated with a command in the CLI application.
 * Stores command execution status, stdin, stdout and stderr descriptors.
 */
@Data
public class CommandData {
    private CommandStatus status;
    private IDescriptor stdin;
    private IDescriptor stdout;
    private IDescriptor stderr;
}
