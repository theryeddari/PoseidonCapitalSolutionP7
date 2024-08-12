package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Test
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setTerm(10d);
		curvePoint.setValue(30d);

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		assertNotNull(curvePoint.getCurveId());
        assertEquals(10, curvePoint.getTerm());

		// Update
		curvePoint.setTerm(20d);
		curvePoint = curvePointRepository.save(curvePoint);
        assertEquals(20, curvePoint.getTerm());

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
        assertFalse(listResult.isEmpty());

		// Delete
		Integer id = Integer.valueOf(curvePoint.getCurveId());
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		assertFalse(curvePointList.isPresent());
	}

}
