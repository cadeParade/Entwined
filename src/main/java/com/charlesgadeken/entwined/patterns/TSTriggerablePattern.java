package com.charlesgadeken.entwined.patterns;

import com.charlesgadeken.entwined.interaction.Triggerable;
import heronarts.lx.LX;

import heronarts.lx.parameter.LXParameterListener;


abstract class TSTriggerablePattern extends TSPattern implements Triggerable {

  static final int PATTERN_MODE_PATTERN = 0; // not implemented
  static final int PATTERN_MODE_TRIGGER = 1; // calls the run loop only when triggered
  static final int PATTERN_MODE_FIRED = 2; // like triggered, but pattern must disable itself when finished
  static final int PATTERN_MODE_CUSTOM = 3; // always calls the run loop

  int patternMode = PATTERN_MODE_TRIGGER;

  boolean triggerableModeEnabled;
  boolean triggered = true;
  double firedTimer = 0;

  TSTriggerablePattern(LX lx) {
    super(lx);
  }

  void onTriggerableModeEnabled() {
    getChannel().getFader().setValue(1);
    if (patternMode == PATTERN_MODE_TRIGGER || patternMode == PATTERN_MODE_FIRED) {
      setCallRun(false);
    }
    triggerableModeEnabled = true;
    triggered = false;
  }

  Triggerable getTriggerable() {
    return this;
  }

  public boolean isTriggered() {
    return triggered;
  }

  public void onTriggered(float strength) {
    if (patternMode == PATTERN_MODE_TRIGGER || patternMode == PATTERN_MODE_FIRED) {
      setCallRun(true);
    }
    triggered = true;
    firedTimer = 0;
  }

  public void onRelease() {
    if (patternMode == PATTERN_MODE_TRIGGER) {
      setCallRun(false);
    }
    triggered = false;
  }

  public void addOutputTriggeredListener(LXParameterListener listener) {
  }
}
