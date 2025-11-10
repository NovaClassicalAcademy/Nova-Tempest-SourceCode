package org.firstinspires.ftc.teamcode.Decode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config.ColorSensorController;
import org.firstinspires.ftc.teamcode.Config.IntakeServo;
import org.firstinspires.ftc.teamcode.Config.OutputServo;
import org.firstinspires.ftc.teamcode.Config.RouletteServoController;

@TeleOp(name = "Concept: Scan Servo", group = "Concept")
@Disabled
public class Orchestrate extends LinearOpMode{
    private final HardwareClass _hardware;
    private final RouletteServoController _rouletteController;
    private final ColorSensorController _colorController;
    private final IntakeServo _intakeServo;
    private final OutputServo _chuckServo;
    public Orchestrate(){
        _hardware = new HardwareClass();
        _colorController = new ColorSensorController(_hardware.NormalizedColorSensor);
        _rouletteController = new RouletteServoController(_hardware.RouletteServo, _colorController);
        _intakeServo = new IntakeServo(_hardware.IntakeUpperLeft, _hardware.IntakeUpperRight, _hardware.IntakeLowerLeft, _hardware.IntakeLowerRight);
        _chuckServo = new OutputServo(_hardware.OutputServo);
    }

    @Override
    public void runOpMode(){

    }
}
