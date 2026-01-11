package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.RobotController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;

@Autonomous(name="Autonomous Pedro Test", group="Test")
public class AutonomousTest extends LinearOpMode {
    private final RobotController _robotController;
    private HardwareMap _hardwareMap;
    private Follower _follower;
    private enum RunStates{
        AtStart, AtCloseLaunch, AtFarLaunch, MovingToZone, AtPickUp, Scooping, Idle, Traveling, LaunchReady, IsLaunching
    }
    public AutonomousTest() {
        _robotController = new RobotController(hardwareMap);
    }

    @Override
    public void runOpMode() {

        telemetry.addData("OpMode", "Autonomous PEDRO TEST");
        telemetry.update();

        waitForStart();
    }
}
