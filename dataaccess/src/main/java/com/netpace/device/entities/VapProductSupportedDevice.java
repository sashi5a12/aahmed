package com.netpace.device.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "vap_product_supported_device")
public class VapProductSupportedDevice extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5809163032690759737L;

    Integer deviceId;
    String deviceName;
    VapProduct product;

    public VapProductSupportedDevice() {
	}

	public VapProductSupportedDevice(String deviceName) {
		super();
		this.deviceName = deviceName;
	}

	@Id
    @Column(name = "device_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    @Column(name = "device_name")
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public VapProduct getProduct() {
		return product;
	}

	public void setProduct(VapProduct product) {
		this.product = product;
	}

	@Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();

        buffer.append("deviceId=[").append(deviceId).append("] ");
        buffer.append("deviceName=[").append(deviceName).append("] ");
        return buffer.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + ((deviceId == null) ? 0 : deviceId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VapProductSupportedDevice)) {
            return false;
        }
        VapProductSupportedDevice equalCheck = (VapProductSupportedDevice) obj;
        if ((deviceId == null && equalCheck.deviceId != null) || (deviceId != null && equalCheck.deviceId == null)) {
            return false;
        }
        if (deviceId != null && !deviceId.equals(equalCheck.deviceId)) {
            return false;
        }
        return true;
    }
}
