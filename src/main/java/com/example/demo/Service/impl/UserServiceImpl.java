package com.example.demo.Service.impl;

import com.example.demo.Exeption.CustomException;
import com.example.demo.Service.JWTService;
import com.example.demo.Service.UserService;
import com.example.demo.dto.card.CardRequest;
import com.example.demo.dto.card.CardResponse;
import com.example.demo.dto.user.response.CreateUserResponseDto;
import com.example.demo.mapper.CardMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Card;
import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardMapper cardMapper;

    private final JWTService jwtService;

    @Override
    public String fromNormalToVIP(String authorizationHeader, CardRequest cardRequest) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Extract the username from the token
        String username = jwtService.extractUsername(token);

        // Fetch the user from the database
        User user = userRepository.findByEmail(username);

        if (user.getUserType()== UserType.NORMAL){
            user.setUserType(UserType.VIP);
            Card card = cardMapper.mapCardCreateRequestDTOToCardEntity(cardRequest);
            card.setUser(user);
            cardRepository.save(card);
            CardResponse cardResponse= cardMapper.mapCardEntityToCardResponseDTO(card);
            user.setCard(card);
            userRepository.save(user);
            return "user "+user.getEmail()+" changed to VIP with card: "+cardResponse.getCardNumber();
        }else {
            return "This user is already VIP";
        }
    }

    @Override
    public String fromVIPtoNormal(String authorizationHeader) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Extract the username from the token
        String username = jwtService.extractUsername(token);

        // Fetch the user from the database
        User user = userRepository.findByEmail(username);

        if (user.getUserType()==UserType.VIP){
            Card card = user.getCard();
            card.setActive(false);
            cardRepository.save(card);
            user.setUserType(UserType.NORMAL);
            userRepository.save(user);
            return "user "+user.getEmail()+" changed to NORMAL, card: "+card.getCardNumber()+" is not active anymore";
        }else {
            return "This user is already NORMAL";
        }
    }









/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public CreateUserResponseDto findUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("not found"));
        return userMapper.mapUserEntityToUserResponseDto(user);
    }

    @Override
    public List<CreateUserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> userMapper.mapUserEntityToUserResponseDto(user)).collect(Collectors.toList());
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username);
//                User user = userRepository.findByUsername(username);
//                if (user == null) {
//                    throw new UsernameNotFoundException("User not found with username: " + username);
//                }
//                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
            }
        };
    }



//    @Override
//    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto, Boolean rememberMe, HttpServletResponse response) {
//
//        if (!isValidEmail(createUserRequestDto.getEmail())) {
//            throw new CustomException("Invalid email format.");
//        }
//
//        User user = mapper.mapCreateUserRequestDtoToUserEntity(createUserRequestDto);
//        User savedUser = userRepository.save(user);
//        CreateUserResponseDto userResponseDto = mapper.mapUserEntityToUserResponseDto(savedUser);
//
//
//        if (rememberMe) {
//            Cookie cookie = new Cookie("rememberMe", user.getEmail());
//            cookie.setMaxAge(7 * 24 * 60 * 60); // 1 week
//            cookie.setHttpOnly(true);
//            cookie.setPath("/");
//            response.addCookie(cookie);
//        }
//
//        return userResponseDto;
//    }
}
