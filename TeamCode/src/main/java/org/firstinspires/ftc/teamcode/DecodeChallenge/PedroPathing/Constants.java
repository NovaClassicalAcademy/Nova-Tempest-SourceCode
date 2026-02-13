package org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.ThreeWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .forwardZeroPowerAcceleration(-30)
            .lateralZeroPowerAcceleration(-75)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.1, 0, 0, 0))
            .headingPIDFCoefficients(new PIDFCoefficients(1, 0, 0.5,0.01))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.025, 0, 0.0001, 0.6, 0.01))
            .centripetalScaling(0.0005)
            .useSecondaryTranslationalPIDF(true)
            .useSecondaryHeadingPIDF(true)
            .useSecondaryDrivePIDF(true)
            .mass(5);

    public static PathConstraints pathConstraints = new PathConstraints(
            0.99,
            100,
            1,
            1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .threeWheelLocalizer(localizerConstants)
                .build();
    }

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .xVelocity(83)
            .yVelocity(61)
            .leftFrontMotorName("FrontLeft")
            .rightFrontMotorName("FrontRight")
            .leftRearMotorName("BackLeft")
            .rightRearMotorName("BackRight")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD);

    public static ThreeWheelConstants localizerConstants = new ThreeWheelConstants()
            .forwardTicksToInches(0.003)
            .strafeTicksToInches(0.003)
            .turnTicksToInches(0.003)
            .leftPodY(7)
            .rightPodY(-7)
            .strafePodX(-2.5)
            .leftEncoder_HardwareMapName("YLeftEncoder")
            .rightEncoder_HardwareMapName("YRightEncoder")
            .strafeEncoder_HardwareMapName("XEncoder")
            .leftEncoderDirection(Encoder.FORWARD)
            .rightEncoderDirection(Encoder.REVERSE)
            .strafeEncoderDirection(Encoder.REVERSE);
}
