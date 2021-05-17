package view;

public class EfmDados {
    
    private static double avaliability = 0.0;
    private static double period = 0.0;
    private static double electricityCost = 0.0;
    private static double demandEnergy = 0.0;
    private static boolean calcAval = false;

    public boolean isCalcAval() {
        return calcAval;
    }

    public void setCalcAval(boolean calcAval) {
        EfmDados.calcAval = calcAval;
    }
    

    public double getAvaliability() {
        return avaliability;
    }

    public void setAvaliability(double avaliability) {
        this.avaliability = avaliability;
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }

    public double getElectricityCost() {
        return electricityCost;
    }

    public void setElectricityCost(double electricityCost) {
        this.electricityCost = electricityCost;
    }

    public double getDemandEnergy() {
        return demandEnergy;
    }

    public void setDemandEnergy(double demandEnergy) {
        EfmDados.demandEnergy = demandEnergy;
    }
    
}
