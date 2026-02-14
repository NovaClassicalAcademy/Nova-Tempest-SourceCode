package org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class BlueFullRunPathing {
    public PathChain PreloadedShoot;
    public PathChain FirstRowAlign;
    public PathChain FirstRowLoad;
    public PathChain FirstRowShoot;
    public PathChain SecondRowAlign;
    public PathChain SecondRowLoad;
    public PathChain SecondRowShoot;
    public PathChain ThirdRowAlign;
    public PathChain ThirdRowLoad;
    public PathChain ThirdRowShoot;
    public PathChain FinishPosition;

    public BlueFullRunPathing(Follower follower) {
        InitPathChain(follower);
        follower.setStartingPose(new Pose(64.000, 8.000, Math.toRadians(270)));
    }

    private void InitPathChain(Follower follower){

        PreloadedShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(64.000, 8.000, Math.toRadians(270)),
                                new Pose(60.000, 25.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(285))
                .build();

        FirstRowAlign = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(60.000, 25.000),
                                new Pose(65.000, 33.000),
                                new Pose(44.000, 33.500)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(285), Math.toRadians(180))
                .build();

        FirstRowLoad = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.000, 33.500),
                                new Pose(17.000, 33.500)
                        )
                ).addParametricCallback(0.0, ()-> follower.setMaxPower(0.20))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        FirstRowShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17.000, 33.500),
                                new Pose(60.000, 25.000)
                        )
                ).addParametricCallback(0.0, ()-> follower.setMaxPower(1))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(285))
                .build();

        SecondRowAlign = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(60.000, 25.000),
                                new Pose(75.000, 60.000),
                                new Pose(44.000, 57.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(285), Math.toRadians(180))
                .build();

        SecondRowLoad = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.000, 57.000),
                                new Pose(17.000, 57.000)
                        )
                ).addParametricCallback(0.1, ()-> follower.setMaxPower(0.20))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        SecondRowShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17.000, 57.000),
                                new Pose(60.000, 25.000)
                        )
                ).addParametricCallback(0.0, ()-> follower.setMaxPower(1))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(285))
                .build();

        ThirdRowAlign = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(60.000, 25.000),
                                new Pose(75.000, 90.000),
                                new Pose(44.000, 80.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(285), Math.toRadians(180))
                .build();

        ThirdRowLoad = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.000, 80.000),
                                new Pose(17.000, 80.000)
                        )
                ).addParametricCallback(0.1, ()-> follower.setMaxPower(0.20))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        ThirdRowShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17.000, 80.000),
                                new Pose(60.000, 25.000)
                        )
                ).addParametricCallback(0.0, ()-> follower.setMaxPower(1))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(285))
                .build();

        FinishPosition = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(60.000, 25.000),
                                new Pose(60.000, 40.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(285), Math.toRadians(270))

                .build();
    }
}