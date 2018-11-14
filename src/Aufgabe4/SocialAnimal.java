package Aufgabe4;

/* Note: There is an opportunity to have this be a subtype of Animal & have Bird/Fish/Insect/Mammal extend SocialAnimal.
 * 		 However since there are Bird/Fish/Insect/Mammal types possible that aren't actually social this would require
 * 		 the expected value of social to be zero for these types and make inSocialGroup() somewhat obsolete. 
 */
public interface SocialAnimal {
	
	// Nachbedingung - Erwartungswert für social() ist immer eine positive Zahl
    int social();
    
    boolean inSocialGroup();
}
