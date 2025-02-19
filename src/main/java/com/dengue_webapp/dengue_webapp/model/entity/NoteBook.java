package com.dengue_webapp.dengue_webapp.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class NoteBook {
    @Id
    @Column(name = "id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="date")
    private String date;

}
/* date: location.state.date || '',
        day: location.state.day || '',
        time: location.state.time || '',
        timePeriod: location.state.timePeriod || 'AM',
        dutyPlace: location.state.dutyPlace || '',
        outgoingTime: location.state.outgoingTime || '',
        outgoingTimePeriod: location.state.outgoingTimePeriod || 'AM',
        ingoingTime: location.state.ingoingTime || '',
        ingoingTimePeriod: location.state.ingoingTimePeriod || 'AM',
        fieldOutTime: location.state.fieldOutTime || '',
        fieldInTime: location.state.fieldInTime || '',
        distance: location.state.distance || '',
        vehicleOption: location.state.vehicleOption || 'Public Transport',
        religion: location.state.religion || '',
        race: location.state.race || '',
        job: location.state.job || '', */