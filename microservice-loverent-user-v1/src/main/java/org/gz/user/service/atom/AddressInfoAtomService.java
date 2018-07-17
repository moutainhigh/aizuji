package org.gz.user.service.atom;

import org.gz.user.entity.AddressInfo;

public interface AddressInfoAtomService {

	AddressInfo queryAddressByUserId(Long userId);

	AddressInfo queryAddressByPrimaryKey(Long addrId);

	void updateAddressByPrimaryKey(AddressInfo addressInfo);

	void updateAddressByUserId(AddressInfo addressInfo);

	void removeAddressByPrimaryKey(Long addrId);

	void removeAddressByUserId(Long userId);

	void insertAddress(AddressInfo addressInfo);

}
