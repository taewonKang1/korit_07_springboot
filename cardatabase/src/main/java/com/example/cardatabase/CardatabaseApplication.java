package com.example.cardatabase;

import com.example.cardatabase.domain.Car;
import com.example.cardatabase.domain.CarRepository;
import com.example.cardatabase.domain.Owner;
import com.example.cardatabase.domain.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(
			CardatabaseApplication.class
	);

	// 여기에 생성자 주입 부분 적겠습니다(그리고 .md파일로 옮기는 것도 함께 하겠습니다)
	private final CarRepository repository;
    private final OwnerRepository ownerRepository;

    public CardatabaseApplication(CarRepository repository, OwnerRepository ownerRepository) {
        this.repository = repository;
        this.ownerRepository = ownerRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		// 소유자 객체를 추가
        Owner owner1 = new Owner("일", "김");
        Owner owner2 = new Owner("이", "김");
        // 다수의 객체를 한 번에 저장하는 메서드 처음 사용해보겠습니다.
        ownerRepository.saveAll(Arrays.asList(owner1, owner2));

        // 그리고 Car의 생성자에 field를 추가했기 때문에 오류 나는 것을 막기위해 owner들을 추가해쭷습니다


		repository.save(new Car("Kia", "Seltos", "Chacol", "370SU5690", 2020, 30000000, owner1));
		repository.save(new Car("Hyundai", "Sonata", "White", "123456", 2025, 25000000, owner2));
		repository.save(new Car("Honda", "CR-V", "Black-White", "987654", 2024, 45000000, owner2));

		for (Car car : repository.findAll()) {
			logger.info("brand : {}, model : {}", car.getBrand(), car.getModel());
		}

	}
}