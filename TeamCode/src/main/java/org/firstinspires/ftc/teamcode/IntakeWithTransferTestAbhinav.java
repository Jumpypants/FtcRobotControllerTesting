package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Intake_With_Transfer_Test_Abhinav", group="Linear OpMode")
public class IntakeWithTransferTestAbhinav extends LinearOpMode {

    private Motor intake = new Motor(hardwareMap, "Intake");
    private Servo transferLeft, transferRight;
    private double power;

    @Override
    public void runOpMode() {

        while (opModeInInit()){
            transferLeft = hardwareMap.get(Servo.class, "LeftServo");
            transferRight = hardwareMap.get(Servo.class, "RightServo");

            transferLeft.setPosition(0.5);
            transferRight.setPosition(0.5);

            telemetry.addData("Transfer - Left position", transferLeft.getPosition());
            telemetry.addData("Transfer - Right position", transferRight.getPosition());
            telemetry.update();
        }

        while (opModeIsActive()) {

            if (gamepad1.left_bumper){
                transferLeft.setPosition(0.9);
            }else {
                transferLeft.setPosition(0.5);
            }

            if (gamepad1.right_bumper){
                transferRight.setPosition(0.2);
            }else{
                transferRight.setPosition(0.5);
            }

            power = gamepad1.right_trigger - gamepad1.left_trigger;

            intake.set(power);


            telemetry.addData("Transfer - Left position", transferLeft.getPosition());
            telemetry.addData("Transfer - Right position", transferRight.getPosition());
            telemetry.addData("Intake Power", power);
            telemetry.update();
        }
    }
}
