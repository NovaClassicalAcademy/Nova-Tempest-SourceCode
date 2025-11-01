package org.firstinspires.ftc.teamcode.Decode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config.ColorSensorController;
import org.firstinspires.ftc.teamcode.Config.RouletteServoController;

@TeleOp(name = "Concept: Scan Servo", group = "Concept")
@Disabled
public class Orchestrate extends LinearOpMode{
    private final HardwareClass _hardware;
    private final RouletteServoController _rouletteController;
    private final ColorSensorController _colorController;
    public Orchestrate(){
        _hardware = new HardwareClass();
        _colorController = new ColorSensorController(_hardware.ColorSensor);
        _rouletteController = new RouletteServoController(_hardware.RouletteServo, _colorController);
    }

    @Override
    public void runOpMode(){

    }
}
