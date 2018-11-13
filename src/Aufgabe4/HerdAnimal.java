package Aufgabe4;

public interface HerdAnimal extends SocialAnimal {

	// keine Zusicherung für getAlpha() - immer entweder null oder ein Tier
    HerdAnimal getAlpha();
    
    /* keine Zusicherung für setAlpha() - Alphatier ist entweder ein Tier oder vielleicht auch null,
     * 									  unter Umständen ist this danach Teil einer Herde mit nur einem Tier */
    /* kein Parameter - wir stellen uns vor, dass das Alpha der Herde entweder this ist oder falls this bereits das Alpha ist,
     * 					das Alpha auf null gesetzt wird */
    void setAlpha();
    
    // Vorbedingung - this ist Teil einer Herde
    void leave();
    // Nachbedingung - this ist kein Teil einer Herde
}
