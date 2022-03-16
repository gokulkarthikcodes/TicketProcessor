package com.sync.ticketprocessor.service;

import com.sync.ticketprocessor.dto.VendorDTO;

import java.util.List;

public interface VendorService {

    VendorDTO createVendor(VendorDTO vendorDTO);

    Boolean deleteVendor(VendorDTO vendorDTO);

    List<VendorDTO> getMyVendors(String username);

    VendorDTO updateVendor(VendorDTO vendorDTO);
}
