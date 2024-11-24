package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class BasicDrive extends LinearOpMode {
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;

    private DcMotorEx inarm;
    private DcMotor outslide;
    private Servo outl;
    private Servo outr;
    private Servo inwheel;

    double pwrX;
    double pwrY;
    double turn;
    double armPos;
    double slidiness;
    double outrotation;
    boolean inForward = false;
    boolean inBackward = false;
    boolean aHeld = false;
    boolean xHeld = false;

    @Override
    public void runOpMode() {
        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");

        inarm = hardwareMap.get(DcMotorEx.class, "inarm");
        outslide = hardwareMap.get(DcMotor.class, "outslide");
        outl = hardwareMap.get(Servo.class, "outl");
        outr = hardwareMap.get(Servo.class, "outr");
        inwheel = hardwareMap.get(Servo.class, "inwheel");

        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        outslide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()){
            pwrX = gamepad1.left_stick_x;
            pwrY = gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;

            armPos = (gamepad1.right_trigger - gamepad1.left_trigger);
            outrotation = ((gamepad1.right_bumper ? 0.5 : 0) - (gamepad1.left_bumper ? 0.5 : 0));
            slidiness = ((gamepad1.y ? 1 : 0) - (gamepad1.b ? 1 : 0));

            if(gamepad1.a && !aHeld){
                if(inForward){
                    inwheel.setPosition(0.5);
                    inForward = false;
                }else{
                    inForward = true;
                    inBackward = false;
                    inwheel.setPosition(1);
                }
                aHeld = true;
            }
            if(gamepad1.x && !xHeld){
                if(inBackward){
                    inwheel.setPosition(0.5);
                    inBackward = false;
                }else{
                    inForward = false;
                    inBackward = true;
                    inwheel.setPosition(-1);
                }
                xHeld = true;
            }

            if(!gamepad1.a){
                aHeld = false;
            }
            if(!gamepad1.b){
                xHeld = false;
            }

            fl.setPower(pwrY-pwrX-turn);
            fr.setPower(pwrY+pwrX+turn);
            bl.setPower(pwrY+pwrX-turn);
            br.setPower(pwrY-pwrX+turn);

            outslide.setPower(slidiness);
            inarm.setPower(armPos/3);
            outl.setPosition(0.5+outrotation);
            outr.setPosition(0.5-outrotation);
        }
    }
}
