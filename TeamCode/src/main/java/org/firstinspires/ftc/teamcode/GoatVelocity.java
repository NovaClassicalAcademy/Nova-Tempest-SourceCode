package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class GoatVelocity {


    private ElapsedTime runtime = new ElapsedTime();
















    private double rpmToTicksPerSec(double rpm, int ticksPerRev, double gearRatio){
        //rpm -> ticks/sec
        return (rpm / 60.0) * ticksPerRev * gearRatio;
    }

    private double ticksPerSecToRPM(double ticksPerSec, int ticksPerRev, double gearRatio){
        return  (ticksPerSec / (ticksPerRev * gearRatio)) * 60.0;
    }




}
