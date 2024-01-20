package io.github.dursunkoc.simplepomodoro.statemachine

import io.github.dursunkoc.simplepomodoro.Constant
import io.github.dursunkoc.simplepomodoro.activity.MainActivity

sealed class State(val name:String, val activity:MainActivity) {
    abstract fun next():State
    abstract fun onActivate():State

    override fun toString(): String {
        return "State: $name"
    }

    class Pomodoro(val sessionCounter:Int, activity:MainActivity)
        :State(name="Pomodoro", activity){
        override fun next(): State {
            this.activity.pomodoroDeactivate(sessionCounter*Constant.DEFAULT_WORK_DURATION_MS);
            return if (sessionCounter%4==0) {
                LongBreak(this, activity).onActivate()
            }else{
                ShortBreak(this, activity).onActivate()
            }
        }

        override fun onActivate():State {
            this.activity.pomodoroActivate(this.sessionCounter)
            return this
        }

        override fun toString(): String {
            return "State: $name($sessionCounter)"
        }
    }
    class ShortBreak(val prev:Pomodoro, activity:MainActivity)
        :State(name="ShortBreak", activity) {
        override fun next(): State {
            this.activity.shortBreakDeactivate();
            return Pomodoro(prev.sessionCounter+1, activity).onActivate();
        }
        override fun onActivate():State {
            this.activity.shortBreakActivate()
            return this
        }
        override fun toString(): String {
            return "State: $name($prev)"
        }
    }

    class LongBreak(val prev:Pomodoro, activity:MainActivity)
        :State(name="LongBreak", activity) {
        override fun next(): State {
            this.activity.longBreakDeactivate()
            return Pomodoro(prev.sessionCounter+1, activity).onActivate()
        }
        override fun onActivate():State {
            this.activity.longBreakActivate()
            return this
        }
        override fun toString(): String {
            return "State: $name($prev)"
        }
    }

    class Completed(activity:MainActivity):State(name="Completed", activity) {
        override fun next(): State {
            return Pomodoro(1, activity).onActivate()
        }
        override fun onActivate():State {
            //TODO!!! implement me!
            return this
        }
    }
}
