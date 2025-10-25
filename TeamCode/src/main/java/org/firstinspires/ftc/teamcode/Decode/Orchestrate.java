package org.firstinspires.ftc.teamcode.Decode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config.RouletteServoController;

@TeleOp
public class Orchestrate {
    private HardwareClass _hardware;
    private RouletteServoController _roulette;
    public Orchestrate(){
        _hardware = new HardwareClass();
        _roulette = new RouletteServoController(_hardware.RouletteServo);
    }
}
