package localeconnect.me.localeconnect;

import java.util.Date;




public class Event {
	
	private String id;
	private String address;
	private String city;
	private String zip;
	private String country;
	private double longtude;
	private double lattiude;
	private Date eventTime;
	private Date eventCheckInTime;
	private Date eventCheckoutTime;
	private boolean verReqIndicator;
	private String prefId;
	
	private String initiatingUserId;
	private String acceptingUserId;
	/**
	 * @return the id
	 */

	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the address
	 */

	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the city
	 */

	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the zip
	 */

	public String getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the country
	 */

	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the longtude
	 */

	public double getLongtude() {
		return longtude;
	}
	/**
	 * @param longtude the longtude to set
	 */
	public void setLongtude(double longtude) {
		this.longtude = longtude;
	}
	/**
	 * @return the lattiude
	 */

	public double getLattiude() {
		return lattiude;
	}
	/**
	 * @param lattiude the lattiude to set
	 */
	public void setLattiude(double lattiude) {
		this.lattiude = lattiude;
	}
	/**
	 * @return the eventTime
	 */

	public Date getEventTime() {
		return eventTime;
	}
	/**
	 * @param eventTime the eventTime to set
	 */
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	/**
	 * @return the eventCheckInTime
	 */

	public Date getEventCheckInTime() {
		return eventCheckInTime;
	}
	/**
	 * @param eventCheckInTime the eventCheckInTime to set
	 */
	public void setEventCheckInTime(Date eventCheckInTime) {
		this.eventCheckInTime = eventCheckInTime;
	}
	/**
	 * @return the eventCheckoutTime
	 */

	public Date getEventCheckoutTime() {
		return eventCheckoutTime;
	}
	/**
	 * @param eventCheckoutTime the eventCheckoutTime to set
	 */
	public void setEventCheckoutTime(Date eventCheckoutTime) {
		this.eventCheckoutTime = eventCheckoutTime;
	}
	/**
	 * @return the verReqIndicator
	 */

	public boolean isVerReqIndicator() {
		return verReqIndicator;
	}
	/**
	 * @param verReqIndicator the verReqIndicator to set
	 */
	public void setVerReqIndicator(boolean verReqIndicator) {
		this.verReqIndicator = verReqIndicator;
	}
	/**
	 * @return the initiatingUserId
	 */

	public String getInitiatingUserId() {
		return initiatingUserId;
	}
	/**
	 * @param initiatingUserId the initiatingUserId to set
	 */
	public void setInitiatingUserId(String initiatingUserId) {
		this.initiatingUserId = initiatingUserId;
	}
	/**
	 * @return the acceptingUserId
	 */

	public String getAcceptingUserId() {
		return acceptingUserId;
	}
	/**
	 * @param acceptingUserId the acceptingUserId to set
	 */
	public void setAcceptingUserId(String acceptingUserId) {
		this.acceptingUserId = acceptingUserId;
	}
	

	public String getPrefId() {
		return prefId;
	}
	public void setPrefId(String prefId) {
		this.prefId = prefId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acceptingUserId == null) ? 0 : acceptingUserId.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((eventCheckInTime == null) ? 0 : eventCheckInTime.hashCode());
		result = prime * result + ((eventCheckoutTime == null) ? 0 : eventCheckoutTime.hashCode());
		result = prime * result + ((eventTime == null) ? 0 : eventTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((initiatingUserId == null) ? 0 : initiatingUserId.hashCode());
		result = prime * result + (int) (lattiude );
		result = prime * result + (int) (longtude );
		result = prime * result + (verReqIndicator ? 1231 : 1237);
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (acceptingUserId == null) {
			if (other.acceptingUserId != null)
				return false;
		} else if (!acceptingUserId.equals(other.acceptingUserId))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (eventCheckInTime == null) {
			if (other.eventCheckInTime != null)
				return false;
		} else if (!eventCheckInTime.equals(other.eventCheckInTime))
			return false;
		if (eventCheckoutTime == null) {
			if (other.eventCheckoutTime != null)
				return false;
		} else if (!eventCheckoutTime.equals(other.eventCheckoutTime))
			return false;
		if (eventTime == null) {
			if (other.eventTime != null)
				return false;
		} else if (!eventTime.equals(other.eventTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (initiatingUserId == null) {
			if (other.initiatingUserId != null)
				return false;
		} else if (!initiatingUserId.equals(other.initiatingUserId))
			return false;
		if (lattiude != other.lattiude)
			return false;
		if (longtude != other.longtude)
			return false;
		if (verReqIndicator != other.verReqIndicator)
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Event [id=" + id + ", address=" + address + ", city=" + city + ", zip=" + zip + ", country=" + country
				+ ", longtude=" + longtude + ", lattiude=" + lattiude + ", eventTime=" + eventTime
				+ ", eventCheckInTime=" + eventCheckInTime + ", eventCheckoutTime=" + eventCheckoutTime
				+ ", verReqIndicator=" + verReqIndicator + ", initiatingUserId=" + initiatingUserId
				+ ", acceptingUserId=" + acceptingUserId + "]";
	}
	
	
	
	
	
	
	
	

}
