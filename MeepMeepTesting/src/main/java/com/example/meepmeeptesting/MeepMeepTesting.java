package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setStartPose(new Pose2d(24, -61, Math.toRadians(90)))
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(24, -61, Math.toRadians(90)))
                        .splineToConstantHeading(new Vector2d(48, -48), Math.toRadians(90))
                        //extend intake arm
                        //start intake servo
                        .lineToY(-40)
                        //pick up first sample
                        //stop intake servo
                        .strafeToLinearHeading(new Vector2d(48, -48), Math.toRadians(270))
                        //start intake servo in reverse
                        //drop sample
                        //stop intake servo
                        //retract intake?
                        .splineToLinearHeading(new Pose2d(40, -24, Math.toRadians(0)), Math.toRadians(0))
                        //extend intake arm^
                        //start intake servo
                        .lineToX(48)
                        //pick up second sample
                        //stop intake servo
                        .strafeToLinearHeading(new Vector2d(48, -48), Math.toRadians(270))
                        //start intake servo in reverse
                        //drop sample
                        //stop intake servo
                        //retract intake?
                        .splineToLinearHeading(new Pose2d(48, -24, Math.toRadians(0)), Math.toRadians(0))
                        .lineToX(56)
                        //pick up third sample
                        .strafeToLinearHeading(new Vector2d(56, -48), Math.toRadians(270))
                        .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}