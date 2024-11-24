package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous
public class OdometryTest extends LinearOpMode {
    public class Arm{
        private DcMotorEx arm;

        public Arm(HardwareMap hardwareMap){
            arm = hardwareMap.get(DcMotorEx.class, "inarm");
            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public class ExtendArm implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    arm.setPower(0.5);
                    initialized = true;
                }

                double pos = arm.getCurrentPosition();
                packet.put("armPos", pos);
                if (pos < 200.0) {
                    return true;
                } else {
                    arm.setPower(0);
                    return false;
                }
            }
        }
        public Action extendArm(){
            return new ExtendArm();
        }

        public class RetractArm implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    arm.setPower(-0.5);
                    initialized = true;
                }

                double pos = arm.getCurrentPosition();
                packet.put("armPos", pos);
                if (pos > 50.0) {
                    return true;
                } else {
                    arm.setPower(0);
                    return false;
                }
            }
        }
        public Action retractArm(){
            return new RetractArm();
        }
    }

    public class InServo{
        private Servo inServo;

        public InServo(HardwareMap hardwareMap){
            inServo = hardwareMap.get(Servo.class, "inwheel");
        }

        public class SpinIn implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                inServo.setPosition(1);
                return false;
            }
        }
        public Action spinIn(){
            return new SpinIn();
        }

        public class SpinStop implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                inServo.setPosition(0.5);
                return false;
            }
        }
        public Action spinStop(){
            return new SpinStop();
        }

        public class SpinOut implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                inServo.setPosition(0);
                return false;
            }
        }
        public Action spinOut(){
            return new SpinOut;
        }
    }

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(24, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Arm arm = new Arm(hardwareMap);
        InServo inServo = new InServo(hardwareMap);
    }
}
