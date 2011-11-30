package sia3.chap02;

import java.util.Collection;
import java.util.Map;

public class OneManBand implements Performer {
	private Collection<Instrument> instruments;
//	private Instrument[] instruments;
//	private Set<Instrument> instruments;
	Map<String, Instrument> mapInstruments;
	
	public OneManBand() {
	}

	public void perform() throws PerformanceException {
		for (Instrument instrument : instruments) {
			instrument.play();
		}
	}

	public void setInstruments(Collection<Instrument> instruments) {
		this.instruments = instruments; 
	}
	
	public void mapPerform() throws PerformanceException {
		for (String key : mapInstruments.keySet()) {
			System.out.print(key + " : ");
			Instrument instrument = mapInstruments.get(key);
			instrument.play();
		}
	}

	public void setMapInstruments(Map<String, Instrument> mapInstruments) {
		this.mapInstruments = mapInstruments;
	}
}
