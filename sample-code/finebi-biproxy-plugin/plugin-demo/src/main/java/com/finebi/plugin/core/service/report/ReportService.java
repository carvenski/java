package com.finebi.plugin.biproxy.core.service.report;

import com.finebi.plugin.biproxy.core.service.report.bean.ReportBean;
import com.finebi.plugin.biproxy.core.service.report.bean.ReportEntryBean;
import com.finebi.plugin.biproxy.core.service.report.bean.UpdateReportsBean;
import java.util.List;

public interface ReportService {
  
  List<ReportBean> getAllReportBeans() throws Exception;
    
  void removeReports(UpdateReportsBean paramUpdateReportsBean) throws Exception;
  
  void addReports(UpdateReportsBean paramUpdateReportsBean) throws Exception;
  
}


/* Location:              E:\jd-gui-windows-1.6.6\fine-plugin-com.finebi.plugin.biproxy-1.0.jar!\com\finebi\plugin\nat\export\captcha\core\service\report\ReportService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */