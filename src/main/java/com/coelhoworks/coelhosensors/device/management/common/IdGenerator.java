package com.coelhoworks.coelhosensors.device.management.common;

import io.hypersistence.tsid.TSID;

import java.util.Optional;

public class IdGenerator {

    private static final TSID.Factory tsidFactory;

    static {
        Optional.ofNullable(System.getenv("tsid.mode"))
                .ifPresent(tsidNode -> System.setProperty("tsid.mode", tsidNode));

        Optional.ofNullable(System.getenv("tsid.mode.count"))
                .ifPresent(tsidNodeCount -> System.setProperty("tsid.mode.count", tsidNodeCount));

        tsidFactory = TSID.Factory.builder().build();
    }

    private IdGenerator(){

    }

    public static TSID genereteTSID(){
        return tsidFactory.generate();
    }
}
