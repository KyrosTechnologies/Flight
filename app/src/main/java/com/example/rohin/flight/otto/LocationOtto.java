package com.example.rohin.flight.otto;

import com.squareup.otto.Bus;

/**
 * Created by Rohin on 28-04-2017.
 */

public class LocationOtto {
    private static Bus sBus;
    public static Bus getBus() {
        if (sBus == null)
            sBus = new Bus();
        return sBus;
    }
}
