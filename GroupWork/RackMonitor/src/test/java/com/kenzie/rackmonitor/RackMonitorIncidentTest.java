package com.kenzie.rackmonitor;

import com.kenzie.rackmonitor.*;
import com.kenzie.rackmonitor.clients.warranty.Warranty;
import com.kenzie.rackmonitor.clients.warranty.WarrantyClient;
import com.kenzie.rackmonitor.clients.warranty.WarrantyNotFoundException;
import com.kenzie.rackmonitor.clients.wingnut.WingnutClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class RackMonitorIncidentTest {
    RackMonitor rackMonitor;
    @Mock
    WingnutClient wingnutClient;
    @Mock
    WarrantyClient warrantyClient;
    @Mock
    Rack rack1;
    Server unhealthyServer = new Server("TEST0001");
    Server shakyServer = new Server("TEST0067");
    Map<Server, Integer> rack1ServerUnits;

    @BeforeEach
    void setUp() {
        openMocks(this);
        rackMonitor = new RackMonitor(new HashSet<>(Arrays.asList(rack1)),
            wingnutClient, warrantyClient, 0.9D, 0.8D);
    }

    @Test
    public void getIncidents_withOneUnhealthyServer_createsOneReplaceIncident() throws Exception {
        // GIVEN
        // The rack is set up with a single unhealthy server
        // We've reported the unhealthy server to Wingnut
        Map<Server, Double> unhealthyMap = new HashMap<>();
        unhealthyMap.put(unhealthyServer, 0.7);

        Warranty warranty = new Warranty("001");

        when(rack1.getHealth()).thenReturn(unhealthyMap);
        when(warrantyClient.getWarrantyForServer(unhealthyServer)).thenReturn(warranty);
        when(rack1.getUnitForServer(unhealthyServer)).thenReturn(1);

        rackMonitor.monitorRacks();

        // WHEN
        Set<HealthIncident> actualIncidents = rackMonitor.getIncidents();

        // THEN
        HealthIncident expected =
            new HealthIncident(unhealthyServer, rack1, 1, RequestAction.REPLACE);
        assertTrue(actualIncidents.contains(expected),
            "Monitoring an unhealthy server should record a REPLACE incident!");
    }

    @Test
    public void getIncidents_withOneShakyServer_createsOneInspectIncident() throws Exception {
        // GIVEN
        // The rack is set up with a single shaky server
        rack1ServerUnits = new HashMap<>();
        rack1ServerUnits.put(shakyServer, 1);
        rack1 = new Rack("RACK01", rack1ServerUnits);
        rackMonitor = new RackMonitor(new HashSet<>(Arrays.asList(rack1)),
            wingnutClient, warrantyClient, 0.9D, 0.8D);
        // We've reported the shaky server to Wingnut
        rackMonitor.monitorRacks();

        // WHEN
        Set<HealthIncident> actualIncidents = rackMonitor.getIncidents();

        // THEN
        HealthIncident expected =
            new HealthIncident(shakyServer, rack1, 1, RequestAction.INSPECT);
        assertTrue(actualIncidents.contains(expected),
            "Monitoring a shaky server should record an INSPECT incident!");
    }

    @Test
    public void getIncidents_withOneHealthyServer_createsNoIncidents() throws Exception {
        Map<Server, Double> healthyMap = new HashMap<>();
        Server healthyServer = new Server("TEST1234");
        healthyMap.put(healthyServer, 0.95);

        when(rack1.getHealth()).thenReturn(healthyMap);

        // WHEN
        rackMonitor.monitorRacks();
        Set<HealthIncident> actualIncidents = rackMonitor.getIncidents();

        // THEN
        assertEquals(0, actualIncidents.size(),
                "Monitoring a healthy server should record no incidents!");
    }

    @Test
    public void monitorRacks_withOneUnhealthyServer_replacesServer() throws Exception {
        // GIVEN
        // The rack is set up with a single unhealthy server

        // WHEN
        rackMonitor.monitorRacks();

        // THEN
        // There were no exceptions
        // No way to tell we called the warrantyClient for the server's Warranty
        // No way to tell we called Wingnut to replace the server
    }

    @Test
    public void monitorRacks_withUnwarrantiedServer_throwsServerException() throws Exception {
        // GIVEN
        Server noWarrantyServer = new Server("TEST0052");
        Map<Server, Integer> rack1ServerUnits = new HashMap<>();
        rack1ServerUnits.put(noWarrantyServer, 1);

        // Stub the Rack methods
        when(rack1.getHealth()).thenReturn(Collections.singletonMap(noWarrantyServer, 0.6));
        when(rack1.getUnitForServer(noWarrantyServer)).thenReturn(1);

        // Stub the WarrantyClient behavior
        when(warrantyClient.getWarrantyForServer(noWarrantyServer))
                .thenThrow(new WarrantyNotFoundException("Warranty not found for server"));

        // WHEN and THEN
        assertThrows(RackMonitorException.class,
                () -> rackMonitor.monitorRacks(),
                "Monitoring a server with no warranty should throw an exception!");
    }
}
