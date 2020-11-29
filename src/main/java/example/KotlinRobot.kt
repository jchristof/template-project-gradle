package example

import ev3dev.actuators.lego.motors.NXTRegulatedMotor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lejos.hardware.port.MotorPort

fun main(args: Array<String>) = runBlocking {
    val job1 = GlobalScope.launch {
        val motor = NXTRegulatedMotor(MotorPort.A)
        motor.brake()
        val motorSpeed = 200
        motor.speed = motorSpeed
        motor.forward()

        delay(2000)

        motor.backward()

        delay(2000)

        motor.stop()
    }

    val job2 = GlobalScope.launch {
        val motor = NXTRegulatedMotor(MotorPort.B)
        motor.brake()
        val motorSpeed = 200
        motor.speed = motorSpeed
        motor.forward()

        delay(2000)

        motor.backward()

        delay(2000)

        motor.stop()
    }

    job1.join()
    job2.join()

    System.exit(0)
}
