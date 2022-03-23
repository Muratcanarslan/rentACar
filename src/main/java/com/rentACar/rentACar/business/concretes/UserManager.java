package com.rentACar.rentACar.business.concretes;

import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.UserService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.core.utilities.exceptions.userExceptions.UserAlreadyExistsException;
import com.rentACar.rentACar.dataAccess.abstracts.UserDao;

@Service
public class UserManager implements UserService{
	
	private UserDao userDao;
	
	public UserManager(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public void checkIfUserAlreadyExistsByEmail(String email) throws UserAlreadyExistsException {
		if(this.userDao.existsByEmail(email)) {
			throw new UserAlreadyExistsException(BusinessMessages.USER_ALREADY_EXISTS_BY_EMAIL + email,"hata");
		}
		
	}

}
