package com.kenzie.rackmonitor;

import com.kenzie.rackmonitor.Rack;
import com.kenzie.rackmonitor.RackMonitor;
import com.kenzie.rackmonitor.RackMonitorException;
import com.kenzie.rackmonitor.clients.warranty.WarrantyClient;
import com.kenzie.rackmonitor.clients.warranty.WarrantyNotFoundException;
import com.kenzie.rackmonitor.clients.wingnut.WingnutClient;
import com.kenzie.rackmonitor.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RackMonitorExceptionTest {
    RackMonitor rackMonitor;
    @Mock
    WingnutClient wingnutClient;
    @Mock
    WarrantyClient warrantyClient;
    @Mock
    Rack mockRack;
    Server testServer = new Server("TEST0001");
    Map<Server, Double> unwarrantiedServerResult = new HashMap<>();

    @BeforeEach
    void setUp() throws Exception {
        initMocks(this);
        unwarrantiedServerResult.put(testServer, 0.75D);
        when(mockRack.getUnitForServer(testServer)).thenReturn(1);
        rackMonitor = new RackMonitor(new HashSet<>(Arrays.asList(mockRack)),
            wingnutClient, warrantyClient, 0.9D, 0.8D);
    }

    @AfterEach
    void verifyNoDependencyCalls() {
        verifyNoMoreInteractions(wingnutClient, warrantyClient);
    }

    @Test
    public void monitorRacks_withUnhealthyUnwarrantiedServer_throwsRackMonitorException() throws Exception {
        when(mockRack.getHealth()).thenReturn(unwarrantiedServerResult);
        // Getting the Warranty will throw an exception
        when(warrantyClient.getWarrantyForServer(testServer)).thenThrow(WarrantyNotFoundException.class);

        // WHEN and THEN
        assertThrows(RackMonitorException.class,
                () -> rackMonitor.monitorRacks(),
                "Unhealthy server without warranty should throw RackMonitorException!");
        verify(warrantyClient,times(1)).getWarrantyForServer(testServer);
    }
}
