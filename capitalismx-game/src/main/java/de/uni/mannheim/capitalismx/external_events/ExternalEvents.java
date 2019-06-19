package de.uni.mannheim.capitalismx.external_events;

import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

/**
 * @author sdupper
 */
public class ExternalEvents {

    public boolean checkEvent1(){
        return false;
    }

    public boolean checkEvent2(){
        return false;
    }

    public boolean checkEvent3(){
        return false;
    }

    public boolean checkEvent4(){
        //probability 0.02
        if(RandomNumberGenerator.getRandomInt(0, 49) == 0){

            return true;
        }else{
            return false;
        }
    }
}
