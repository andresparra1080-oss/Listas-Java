package filtro;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.ListaMiembroDAO;

@WebFilter("/*")
public class SeguridadFilter implements Filter {

    // rutas que NO necesitan sesión
    private static final Set<String> PUBLICAS = new HashSet<>(Arrays.asList(
        "/index.jsp",
        "/registro.jsp",
        "/CtrolValidar",
        "/ControladorUsuario"
    ));

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String contextPath = request.getContextPath();
        String ruta        = request.getRequestURI()
                                    .substring(contextPath.length());

        // ── 1. Recursos estáticos — siempre pasan ─────────────────────────
        if (ruta.endsWith(".css") || ruta.endsWith(".js") ||
            ruta.endsWith(".png") || ruta.endsWith(".ico") ||
            ruta.startsWith("/resources/")) {
            chain.doFilter(request, response);
            return;
        }

        // ── 2. Rutas públicas — siempre pasan ─────────────────────────────
        for (String publica : PUBLICAS) {
            if (ruta.equals(publica) || ruta.startsWith(publica + "?")) {
                chain.doFilter(request, response);
                return;
            }
        }

        // ── 3. Sin sesión — al login ───────────────────────────────────────
        HttpSession sesion = request.getSession(false);
        Integer idUsuario  = (sesion != null)
                             ? (Integer) sesion.getAttribute("idUsuario")
                             : null;

        if (idUsuario == null) {
            response.sendRedirect(contextPath + "/index.jsp");
            return;
        }

        // ── 4. Verificar membresía si la ruta involucra una lista ──────────
        if (rutaInvolucraSLista(ruta)) {
            String sIdLista = request.getParameter("id");
            if (sIdLista == null) {
                sIdLista = request.getParameter("idLista");
            }

            if (sIdLista != null && !sIdLista.isEmpty()) {
                try {
                    int idLista = Integer.parseInt(sIdLista);
                    if (!new ListaMiembroDAO().esMiembro(idLista, idUsuario)) {
                        response.sendRedirect(contextPath + "/misListas.jsp?error=acceso");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(contextPath + "/misListas.jsp");
                    return;
                }
            }
        }

        // ── 5. Todo correcto — continuar ───────────────────────────────────
        chain.doFilter(request, response);
    }

    private boolean rutaInvolucraSLista(String ruta) {
        return ruta.contains("listaItems") ||
               ruta.contains("miembros")   ||
               ruta.contains("CtrolItem")  ||
               ruta.contains("CtrolLista") ||
               ruta.contains("CtrolRol");
    }

    @Override public void init(FilterConfig fc) throws ServletException {}
    @Override public void destroy() {}
}