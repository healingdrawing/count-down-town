package io.guthub.healingdrawing.countdowntown.lwjgl3

import timer.GameTimer
import java.time.LocalDateTime
import java.time.LocalTime

class DesktopTimer : GameTimer(){
    override fun show():String = when(state){
        states.stopped -> LocalTime.of(h,m,s).format(formatter)
        states.working -> {
            val text = dts(LocalDateTime.now(),fullTime)
            if (text == ""){
                state = states.melody
            }
            text
        }
        states.paused -> {
            pause()
        }
        states.melody -> "tune:${tune()+1} ${showRepeats()}"
    }
    
    override fun start(){
        when(state){
            states.stopped -> {
                state = states.working
                fullTime()
            }
            states.working -> {
                state = states.paused
                pauseTime()
            }
            states.paused -> {
                state = states.working
            }
            states.melody -> state = states.stopped
        }
    }
    override fun pause():String{
        fullTime = LocalDateTime.now().plusHours(pauseTime.hour.toLong()).plusMinutes(pauseTime.minute.toLong()).plusSeconds(pauseTime.second.toLong())
        return pauseTime.format(formatter)
    }
    override fun melody() = state == states.melody
    override fun stop(){state = states.stopped}
}