package ui;


import java.util.List;

import dao.ChuyenDeDAO;
import dao.HocVienDAO;
import dao.KhoaHocDAO;
import dao.NguoiHocDAO;
import dao.NhanVienDAO;
import dao.ThongKeDAO;
import entity.ChuyenDe;
import entity.HocVien;
import entity.KhoaHoc;
import entity.NhanVien;

public class testt {
public static void main(String[] args) {
	ChuyenDeDAO cddao = new ChuyenDeDAO();
	NhanVienDAO nvdao = new NhanVienDAO();
	HocVienDAO hVienDAO = new HocVienDAO();
	KhoaHocDAO khoaHocDAO = new KhoaHocDAO();
//	khoaHocDAO.update(new KhoaHoc(7, "C0001", 1200, 180, "2023-07-10", null, null, null));
//	khoaHocDAO.update(new KhoaHoc(7,"C0001",1200, 180, 2023/07/10,"Khóa học lập trình mới","HNHT1",2023/15/10,7));
//	hVienDAO.update(new HocVien(6,	5,	"NH003",	9.0));
//	List<HocVien> ls = hVienDAO.selectAll();
//	for(HocVien hv : ls){
//      System.out.println("=>"+hv.toString());
//  }
	
//	nHocDAO.selectNotInCourse("NH001",3);
//	nvdao.update(new NhanVien("TH2", "Thường Triều", "333", false));
//  List<NhanVien> ls = nvdao.selectAll();
//	for(NhanVien nv : ls){
//      System.out.println("=>"+nv.toString());
//  }
	
//    cddao.insert(new ChuyenDe("WEB05", "Lập trình Web", 100.0, 45, "", "Học lập trình web vui vẻ!!"));
//    List<ChuyenDe> ls = cddao.selectAll();
//    for(ChuyenDe cd : ls){
//        System.out.println("=>"+cd.toString());
//    }
//	cddao.update(new ChuyenDe("WEB05", "Lập trình Web", 100.0, 40, "", "Học lập trình web vui vẻ với IT!!"));;
//    cddao.insert(new ChuyenDe("WEB05", "Lập trình Web", 100.0, 45, "", "Học lập trình web vui vẻ!!"));
//	
//    List<ChuyenDe> ls = cddao.selectAll();
//    for(ChuyenDe cd : ls){
//        System.out.println("=>"+cd.toString());
//    }
//	        ChuyenDeDAO dao = new ChuyenDeDAO();
//	        dao.insert(new ChuyenDe("WEB01", "Lập trình Web", 18, 45,"gg","Học lập trình web vui vẻ!!"));
//    System.out.println("=> ");
//
//	ThongKeDAO tKeDAO = new ThongKeDAO();
//List<Object[]> list = tKeDAO.getDiemChuyenDe();
//for (Object[] objects : list) {
//    System.out.println("=> " + objects[0]+"-"+objects[1]+"-"+objects[2]);
//}
//	        List<NhanVien> list = dao.selectAll();
//	        for (NhanVien nhanVien : list) {
//	            System.out.println("=> " + nhanVien.toString());
//	        }
	    
	
}

}
