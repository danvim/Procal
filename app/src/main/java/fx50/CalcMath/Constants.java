package fx50.CalcMath;

import org.nevec.rjm.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Constants
 */
public enum Constants {
    pi          (BigDecimalMath.pi(new MathContext(20)),
            "pi","-","π"),
    exp         (BigDecimalMath.exp(new MathContext(20)),
            "Euler's number","-","ⅇ"),
    m_p         (new BigDecimal("1.67262171E-27"),
            "Proton mass","kg","mₚ"),
    m_n         (new BigDecimal("1.67492728E-27"),
            "Neutron mass","kg","mₙ"),
    m_e         (new BigDecimal("9.1093826E-31"),
            "Electron mass","kg","me"),
    m_mu        (new BigDecimal("1.8835314E-28"),
            "Muon mass","kg","mµ"),
    a_0         (new BigDecimal("0.5291772108E-10"),
            "Bohr radius","m","a₀"),
    h           (new BigDecimal("6.6260693E-34"),
            "Planck constant","Js","ℎ"),
    mu_N        (new BigDecimal("5.05078343E-27"),
            "Nuclear magneton","J/T","μɴ"),
    mu_B        (new BigDecimal("927.400949E-26"),
            "Bohr magneton","J/T","μʙ"),
    h_stroke    (new BigDecimal("1.05457168E-34"),
            "Planck constant, rationalized","Js","ℏ"),
    alpha       (new BigDecimal("7.297352568E-3"),
            "Fine-structure constant","-","α"),
    r_e         (new BigDecimal("2.817940325E-15"),
            "Classical electron radius","m","re"),
    lambda_c    (new BigDecimal("2.426310238E-12"),
            "Compton wavelength","m","λc"),
    gamma_p     (new BigDecimal("2.67522205E8"),
            "Proton gyromagnetic ratio","1/s/T","γₚ"),
    lambda_cp   (new BigDecimal("1.3214098555E-15"),
            "Proton Compton wavelength","m","λcₚ"),
    lambda_cn   (new BigDecimal("1.3195909067E-15"),
            "Neutron Compton wavelength","m","λcₙ"),
    R_inf       (new BigDecimal("10973931.568525"),
            "Rydberg constant","1/m","R∞"),
    u           (new BigDecimal("1.66053886E-27"),
            "Atomic mass constant","kg","υ"),
    mu_p        (new BigDecimal("1.41060671E-26"),
            "Proton magnetic moment","J/T","μₚ"),
    mu_e        (new BigDecimal("-928.476412E-26"),
            "Electron magnetic moment","J/T","μe"),
    mu_n        (new BigDecimal("-0.96623645E-26"),
            "Neutron magnetic moment","J/T","μₙ"),
    mu_mu       (new BigDecimal("-4.49044799E-26"),
            "Muon magnetic moment","J/T","μµ"),
    F           (new BigDecimal("96485.3383"),
            "Faraday constant","C/mol","ℱ"),
    e           (new BigDecimal("1.60217653E-19"),
            "Elementary charge","C","ℯ"),
    N_A         (new BigDecimal("1.60217653E-19"),
            "Avogadro constant","1/mol","Nᴀ"),
    k           (new BigDecimal("1.3806505E-23"),
            "Boltzmann constant","J/K","ƙ"),
    V_m         (new BigDecimal("22.413996E-3"),
            "Molar volume of ideal gas","m^3/mol","Vm"),
    R           (new BigDecimal("8.314472"),
            "Molar gas constant","J/mol/K","ℛ"),
    c_0         (new BigDecimal("299792458"),
            "Speed of light in vacuum","m/s","c₀"),
    c_1         (new BigDecimal("3.74177138E-16"),
            "First radiation constant","Wm^2","c₁"),
    c_2         (new BigDecimal("1.4387752E-2"),
            "Second radiation constant","mK","c₂"),
    sigma       (new BigDecimal("5.670400E-8"),
            "Stefan-Boltzmann constant","Wm^2/K^4","σ"),
    epsilon_0   (new BigDecimal("8.854187817E-12"),
            "Electric constant","F/m","ℇ₀"),
    mu_0        (new BigDecimal("12.566370614E-7"),
            "Magnetic constant","N/A^2","μ₀"),
    phi_0       (new BigDecimal("2.06783372E-15"),
            "Magnetic flux quantum","Wb","φ₀"),
    g           (new BigDecimal("9.80665"),
            "Standard acceleration of gravity","m/s^2","g"),
    G_0         (new BigDecimal("7.748091733E-5"),
            "Conductance quantum","S","G₀"),
    Z_0         (new BigDecimal("376.730313461"),
            "Characteristic impedance of vacuum","Omega","Z₀"),
    t           (new BigDecimal("273.15"),
            "Celsius temperature","K","t"),
    G           (new BigDecimal("6.6742E-11"),
            "Newtonian constant of gravity","m^3/kg/s^2","G"),
    atm         (new BigDecimal("101325"),
            "Standard atmosphere","Pa","atm");

    public BigDecimal value;
    public String name;
    public String unit;
    public String display;

    Constants(BigDecimal value, String name, String unit, String display) {
        this.value = value;
        this.name = name;
        this.unit = unit;
        this.display = display;
    }

    public BigDecimal getValue() {
        return value;
    }
    public String getDisplay() {
        return display;
    }
}
