package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.ScooperController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;

@Autonomous (name= "Scooper Debugger", group = "Test")
public class DebuggingIntake extends OpMode {
    private ScooperController _scooper;
    private RobotMapping _rb;
    private ElapsedTime _timer;


    @Override
    public void init() {
        _rb = new RobotMapping(hardwareMap);
        _scooper = new ScooperController(_rb.Scooper);
        _timer = new ElapsedTime();
    }

    @Override
    public void loop() {
        if (_timer.seconds() == 0) {
            _timer.reset();
        }
        if (_timer.seconds() <= 5) {
            _scooper.ScoopUp();
            telemetry.addLine("Scoops Up");
        }
        if (_timer.seconds() > 5){
            _scooper.ScoopDown();
            telemetry.addLine("Scoops Down");
        }
    }

}
