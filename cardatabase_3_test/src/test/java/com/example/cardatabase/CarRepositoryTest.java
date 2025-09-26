package com.example.cardatabase;

import com.example.cardatabase.domain.Car;
import com.example.cardatabase.domain.CarRepository;
import com.example.cardatabase.domain.Owner;
import com.example.cardatabase.domain.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    @DisplayName("차량 저장 메서드 테스트")
    void safeCar() {
        // given - 제반 준비 사항
        // Car Entity를 확인해봤을 때 field로 Owner를 요구하기 때문에
        // 얘부터 먼저 만들고 -> ownerRepository에 저장
        Owner owner = new Owner("Gemini", "GPT");
        ownerRepository.save(owner);
        // 그리고 Car 객체 생성
        Car car = new Car("Ford", "Mustang", "Red", "ABCDEF", 2021, 567890, owner);

        // when - 테스트 실행
        // 저장이 되었는가를 확인하기 위한 부분
        carRepository.save(car);
        // then - 그 결과가 어떠할지
        assertThat(carRepository.findById(car.getId())).isPresent();        // 이건 그냥 결과값이 하나일테니까.

        assertThat(carRepository.findById(car.getId()).get().getBrand()).isEqualTo("Ford");
    }

    @Test
    @DisplayName("차량 삭제 메서드 테스트")
    void deleteCar() {
        // given -> Owner 객체 생성 / 저장 -> Car 객체 생성 / 저장
        Owner owner = new Owner("Gemini", "GPT");
        ownerRepository.save(owner);
        Car car = new Car("Ford", "Mustang", "Red", "ABCDEF", 2021, 567890, owner);
        carRepository.save(car);
        // when -> 삭제
//        carRepository.deleteAll();
        carRepository.deleteById(car.getId());
        // then -> 삭제가 올바로 되었는지 검증하는 assertThat() 구분
//        assertThat(ownerRepository.count()).isEqualTo(0);
        assertThat(ownerRepository.count()).isEqualTo(3);
    }

    @Test
    @DisplayName("브랜드 검색 메서드 테스트")
    void findByBrandShouldReturnCar() {
        // given - Owner 하나 생성 및 저장 / Car 객체 3 대 생성 및 저장
        Owner owner = new Owner("Gemini", "GPT");
        ownerRepository.save(owner);
        Car car1 = new Car("Ford", "Mustang", "Red", "ABCDEF", 2021, 567890, owner);
        carRepository.save(car1);
        Car car2 = new Car("Ford", "Carnival", "Blue", "BBBDEF", 2024, 599999, owner);
        carRepository.save(car2);
        Car car3 = new Car("Kia", "Lei", "White", "DDCDEF", 2025, 890, owner);
        carRepository.save(car3);
        // when - carRepository.findByBrand("브랜드명") -> 근데 얘 결과 자료형이 뭐였나요.
        //      list라는 것을 확인할 수 있는데,
        List<Car> cars = carRepository.findByBrand("Ford");
        // then에서의 검증은 list 내부의 element의 자료형이 Car 객체일테니까,
        // 그 객체의 getBrand()의 결과값이 우리가 findByBrand()의 argument로 쓴 값과 동일한지를 체크할 수 있겠네요.
        assertThat(cars.get(0).getBrand()).isEqualTo("Ford");
//        assertThat(cars.size()).isEqualTo(2);

    }
}
