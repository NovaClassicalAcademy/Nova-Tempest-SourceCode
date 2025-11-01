/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/// TODO: REFACTOR TO FOCUS ON THE JOB OF THE CENTER BALL ROULETTE SERVO

package org.firstinspires.ftc.teamcode.Config;

import android.graphics.Color;
import android.telephony.mbms.MbmsErrors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.WebcamConfiguration;

import java.util.Hashtable;

/*
 * This OpMode scans a single servo back and forward until Stop is pressed.
 * The code is structured as a LinearOpMode
 * INCREMENT sets how much to increase/decrease the servo position each cycle
 * CYCLE_MS sets the update period.
 *
 * This code assumes a Servo configured with the name "left_hand" as is found on a Robot.
 *
 * NOTE: When any servo position is set, ALL attached servos are activated, so ensure that any other
 * connected servos are able to move freely before running this test.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */
@TeleOp(name = "Concept: Scan Servo", group = "Concept")
public class RouletteServoController extends LinearOpMode {

    ///TODO: TEST AND ADJUST VALUES.

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle. check cycle every 50ms
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    // Define class members
    double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    boolean rampUp = true;
    private Servo _servo;
    private ColorSensorController _colorSensor;
    private double[] _intakePosition;
    private final double _position1 = 0.0;
    private final double _position2 = 0.1;
    private final double _position3 = 0.3;
    private final double get_position3 = 0.2; ///TODO: these values need to be adjusted once we find the values.
    private ArtifactColor[] _colorPosition;

    ///TODO: work on how to make dictionaries.

    public RouletteServoController(Servo servo, ColorSensorController colorSensor){
        _servo = servo;
        _colorSensor = colorSensor;
        _colorPosition = new ArtifactColor[3];

        _intakePosition = new double[3];
        _intakePosition[0] = _position1;
        _intakePosition[1] = _position2;
        _intakePosition[2] = _position3;
    }

    public void InitializeRoulette(){
        int position = 0;

        while (position < 3){
            MoveToPosition(position);
            ArtifactColor color = _colorSensor.GetColor();
            SetColorPosition(position, color);
            position++;
        }
    }
    private void SetColorPosition(int position, ArtifactColor color){
        if (position > 2 || position < 0){
            throw new IndexOutOfBoundsException("Value should be between 1 and 3");
        }
        _colorPosition[position] = color;
    }
    private void MoveToPosition(int position){
        if (position > 2 || position < 0){
            throw new IndexOutOfBoundsException("Value should be between 1 and 3");
        }
        double servoPosition = _intakePosition[position];
        _servo.setPosition(servoPosition);
    }

    @Override
    public void runOpMode() {

        // Wait for the start button
        telemetry.addData(">", "Press Start to scan Servo." );
        telemetry.update();
        waitForStart();

        // Scan servo till stop pressed.
        while(opModeIsActive()){

            // slew the servo, according to the rampUp (direction) variable.
            if (rampUp) {
                // Keep stepping up until we hit the max value.
                position += INCREMENT ;
                if (position >= MAX_POS ) {
                    position = MAX_POS;
                    rampUp = !rampUp;   // Switch ramp direction
                }
            }
            else {
                // Keep stepping down until we hit the min value.
                position -= INCREMENT ;
                if (position <= MIN_POS ) {
                    position = MIN_POS;
                    rampUp = !rampUp;  // Switch ramp direction
                }
            }

            // Display the current value
            telemetry.addData("Servo Position", "%5.2f", position);
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();

            // Set the servo to the new position and pause;
            _servo.setPosition(position);
            sleep(CYCLE_MS);
            idle();
        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
