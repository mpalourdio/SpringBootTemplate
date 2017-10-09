/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.springboottemplate.controllers;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class PoiController {

    private static final String FILENAME = "excel_file.xls";

    /**
     * The point here is to avoid injecting HttpServletResponse
     * because ResponseEntity has convenient methods to set
     * the http response caracteristics.
     */

    @GetMapping(value = "/download", produces = "application/vnd.ms-excel")
    public ResponseEntity<byte[]> donwloadExcel() throws IOException {
        HSSFWorkbook workbook = generateExcelFile();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentDispositionFormData("attachment", FILENAME);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        byte[] responseBody = outputStream.toByteArray();
        responseHeaders.setContentLength(responseBody.length); // useful ?

        return new ResponseEntity<>(responseBody, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/download2")
    public void donwloadExcelFile(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = generateExcelFile();

        response.setHeader("Content-Disposition", "attachement; filename=\"" + FILENAME + "\"");

        //"produces" in @GetMapping does not set the content-type with HttpServletResponse
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        workbook.write(response.getOutputStream());
    }

    private HSSFWorkbook generateExcelFile() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("POI Worksheet");
        HSSFRow row = worksheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("cell value");

        return workbook;
    }
}
