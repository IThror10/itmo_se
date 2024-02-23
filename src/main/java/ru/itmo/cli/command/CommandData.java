package ru.itmo.cli.command;

import ru.itmo.cli.descriptor.IDescriptor;

public record CommandData(
    CommandStatus status,
    IDescriptor stdin,
    IDescriptor stdout,
    IDescriptor stderr
) { }
