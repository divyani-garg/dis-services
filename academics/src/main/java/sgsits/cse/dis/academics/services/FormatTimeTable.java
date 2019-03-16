package sgsits.cse.dis.academics.services;

import java.util.ArrayList;
import java.util.List;

import sgsits.cse.dis.academics.model.ExtraClassTimeTable;
import sgsits.cse.dis.academics.model.SemesterTimeTable;
import sgsits.cse.dis.academics.model.response.SemesterTimeTableResponse;

public class FormatTimeTable {

	public List<SemesterTimeTableResponse> formatTT(List<SemesterTimeTable> semtimetable) {
		List<SemesterTimeTableResponse> result = new ArrayList<SemesterTimeTableResponse>();
		for (SemesterTimeTable semTT : semtimetable) {
			SemesterTimeTableResponse sem = new SemesterTimeTableResponse();
			sem.setSubjectCode(semTT.getSubjectCode());
			sem.setTo(semTT.getTo());
			sem.setFrom(semTT.getFrom());
			sem.setDay(semTT.getDay());
			sem.setType(semTT.getType());
			sem.setFaculty1(semTT.getFaculty1());
			sem.setFaculty2(semTT.getFaculty2());
			sem.setFaculty3(semTT.getFaculty3());
			sem.setLabTechnician(semTT.getLabTechnician());
			sem.setTa(semTT.getTa());
			sem.setBatch(semTT.getBatch());
			sem.setLocation(semTT.getLocation());
			sem.setColor("original");
			result.add(sem);
		}
		return result;
	}

	public List<SemesterTimeTableResponse> formatExtraTT(List<ExtraClassTimeTable> extraClassTimeTable,
			List<SemesterTimeTableResponse> result) {
		for (ExtraClassTimeTable extraClass : extraClassTimeTable) {
			SemesterTimeTableResponse extra = new SemesterTimeTableResponse();
			extra.setSubjectCode(extraClass.getSubjectCode());
			extra.setTo(extraClass.getTo());
			extra.setFrom(extraClass.getFrom());
			extra.setDay(extraClass.getDay());
			extra.setType(extraClass.getType());
			extra.setFaculty1(extraClass.getFaculty1());
			extra.setFaculty2(extraClass.getFaculty2());
			extra.setFaculty3(extraClass.getFaculty3());
			extra.setLabTechnician(extraClass.getLabTechnician());
			extra.setTa(extraClass.getTa());
			extra.setBatch(extraClass.getBatch());
			extra.setLocation(extraClass.getLocation());
			extra.setColor(extraClass.getWhich());
			result.add(extra);
		}
		return result;
	}
}
