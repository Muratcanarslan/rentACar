package com.rentACar.rentACar.business.abstracts;

import com.rentACar.rentACar.core.utilities.exceptions.userExceptions.UserAlreadyExistsException;

public interface UserService {
	
	void checkIfUserAlreadyExistsByEmail(String email) throws UserAlreadyExistsException;
}
