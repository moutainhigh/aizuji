package org.gz.user.service.atom;

import org.gz.user.dto.ContactInfoQueryDto;

public interface ContactInfoAtomService {

	public ContactInfoQueryDto queryContactInfoByPage(ContactInfoQueryDto queryDto);
}
