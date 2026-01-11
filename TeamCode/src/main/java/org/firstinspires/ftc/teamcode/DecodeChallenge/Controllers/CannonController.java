package org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RPMManager;

public class CannonController {

    private final DcMotorEx _motor;
    private final RPMManager _rpmMgr;
    private double _targetRPM;
    private double _rpmTolerance;
    public CannonController(DcMotorEx motor) {
        _motor = motor;
        _rpmMgr = new RPMManager(_motor, 300);
    }

    public void SpinUp(){
        _motor.setPower(1.0);
    }

    public void Stop(){
        _motor.setPower(0.0);
    }

    public void SetRPM(double targetRPM, double maxRPM) {
        _targetRPM = targetRPM;
        _rpmTolerance = Range.clip(targetRPM * 0.1, 0, 100);

        double power = targetRPM / maxRPM;
        _motor.setPower(power);
    }
    public boolean IsReadyForLaunch(){
        RPMManager.RPMStatus status = _rpmMgr.Update(_targetRPM, _rpmTolerance, 5);

        switch(status){
            case REACHED:
                return true;

            case TIMEOUT:
                return true;
        }

        return false;
    }
}
