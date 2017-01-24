package fx50.CalcMath;

import org.nevec.rjm.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;

import static dcheungaa.procal.Tokens.fromUnicode;

/**
 * Constants
 */
public enum Constants {
    pi          (BigDecimalMath.pi(new MathContext(20)),
            "pi","-",fromUnicode(0x03C0)),
    exp         (BigDecimalMath.exp(new MathContext(20)),
            "Euler's number","-",fromUnicode(0x8519)),
    m_p         (new BigDecimal("1.67262171E-27"),
            "Proton mass","kg","m" + fromUnicode(0x209A)),
    m_n         (new BigDecimal("1.67492728E-27"),
            "Neutron mass","kg","m" + fromUnicode(0x2099)),
    m_e         (new BigDecimal("9.1093826E-31"),
            "Electron mass","kg","me"),
    m_mu        (new BigDecimal("1.8835314E-28"),
            "Muon mass","kg","m" + fromUnicode(0x00B5)),
    a_0         (new BigDecimal("0.5291772108E-10"),
            "Bohr radius","m","a" + fromUnicode(0x2080)),
    h           (new BigDecimal("6.6260693E-34"),
            "Planck constant","Js",fromUnicode(0x210E)),
    mu_N        (new BigDecimal("5.05078343E-27"),
            "Nuclear magneton","J/T",fromUnicode(0x03BC, 0x274)),
    mu_B        (new BigDecimal("927.400949E-26"),
            "Bohr magneton","J/T",fromUnicode(0x03BC, 0x299)),
    h_stroke    (new BigDecimal("1.05457168E-34"),
            "Planck constant, rationalized","Js",fromUnicode(0x210F)),
    alpha       (new BigDecimal("7.297352568E-3"),
            "Fine-structure constant","-",fromUnicode(0x03B1)),
    r_e         (new BigDecimal("2.817940325E-15"),
            "Classical electron radius","m","re"),
    lambda_c    (new BigDecimal("2.426310238E-12"),
            "Compton wavelength","m",fromUnicode(0x03BB, 0x0063)),
    gamma_p     (new BigDecimal("2.67522205E8"),
            "Proton gyromagnetic ratio","1/s/T",fromUnicode(0x03B3, 0x209A)),
    lambda_cp   (new BigDecimal("1.3214098555E-15"),
            "Proton Compton wavelength","m",fromUnicode(0x03BB, 0x0063, 0x209A)),
    lambda_cn   (new BigDecimal("1.3195909067E-15"),
            "Neutron Compton wavelength","m",fromUnicode(0x03BB, 0x0063, 0x2099)),
    R_inf       (new BigDecimal("10973931.568525"),
            "Rydberg constant","1/m","R" + fromUnicode(0x221E)),
    u           (new BigDecimal("1.66053886E-27"),
            "Atomic mass constant","kg",fromUnicode(0x028B)),
    mu_p        (new BigDecimal("1.41060671E-26"),
            "Proton magnetic moment","J/T",fromUnicode(0x03BC, 0x209A)),
    mu_e        (new BigDecimal("-928.476412E-26"),
            "Electron magnetic moment","J/T",fromUnicode(0x03BC, 0x0065)),
    mu_n        (new BigDecimal("-0.96623645E-26"),
            "Neutron magnetic moment","J/T",fromUnicode(0x03BC, 0x2099)),
    mu_mu       (new BigDecimal("-4.49044799E-26"),
            "Muon magnetic moment","J/T",fromUnicode(0x03BC, 0x00B5)),
    F           (new BigDecimal("96485.3383"),
            "Faraday constant","C/mol",fromUnicode(0x2131)),
    e           (new BigDecimal("1.60217653E-19"),
            "Elementary charge","C",fromUnicode(0x212F)),
    N_A         (new BigDecimal("1.60217653E-19"),
            "Avogadro constant","1/mol","N" + fromUnicode(0x1D00)),
    k           (new BigDecimal("1.3806505E-23"),
            "Boltzmann constant","J/K",fromUnicode(0x0199)),
    V_m         (new BigDecimal("22.413996E-3"),
            "Molar volume of ideal gas","m^3/mol","Vm"),
    R           (new BigDecimal("8.314472"),
            "Molar gas constant","J/mol/K",fromUnicode(0x211B)),
    c_0         (new BigDecimal("299792458"),
            "Speed of light in vacuum","m/s","c" + fromUnicode(0x2080)),
    c_1         (new BigDecimal("3.74177138E-16"),
            "First radiation constant","Wm^2","c" + fromUnicode(0x2081)),
    c_2         (new BigDecimal("1.4387752E-2"),
            "Second radiation constant","mK","c" + fromUnicode(0x2082)),
    sigma       (new BigDecimal("5.670400E-8"),
            "Stefan-Boltzmann constant","Wm^2/K^4",fromUnicode(0x03C3)),
    epsilon_0   (new BigDecimal("8.854187817E-12"),
            "Electric constant","F/m",fromUnicode(0x2107, 0x2080)),
    mu_0        (new BigDecimal("12.566370614E-7"),
            "Magnetic constant","N/A^2",fromUnicode(0x03BC, 0x2080)),
    phi_0       (new BigDecimal("2.06783372E-15"),
            "Magnetic flux quantum","Wb",fromUnicode(0x03C6, 0x2080)),
    g           (new BigDecimal("9.80665"),
            "Standard acceleration of gravity","m/s^2","g"),
    G_0         (new BigDecimal("7.748091733E-5"),
            "Conductance quantum","S","G" + fromUnicode(0x2080)),
    Z_0         (new BigDecimal("376.730313461"),
            "Characteristic impedance of vacuum","Omega","Z" + fromUnicode(0x2080)),
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
